<template>
  <div>
    <b-container fluid id="container-stats-page">
      <b-row class="my-2">
        <b-col cols="auto" class="py-0 my-0 mt-auto">
          <h1 class="align-text-bottom py-0 my-0">
            <span class="align-text-bottom">{{ this.statType.title }}</span>
          </h1>
        </b-col>
        <b-col cols="auto" class="mt-auto" v-if="sortColumns.length > 0">
          <h5 class="mr-2 my-0" style="display: inline-block">Sorting by:</h5>
          <span v-for="(col, index) in sortColumns" :key="col.key">
            <span style="font-weight: bold">{{ col.label}} </span>
            <span>({{col.sortDirection}})</span>
            <span v-if="index < sortColumns.length - 1">, </span>
          </span>
        </b-col>
        <b-col cols="auto" class="ml-auto mt-auto">
          <b-dropdown text="Choose Columns" variant="outline-dark" size="sm" class="mr-2">
            <b-dropdown-form>
              <b-form-group>
                <b-form-checkbox-group v-model="activeColumns">
                  <b-form-checkbox v-for="col in optionalColumns" :key="col.key" :value="col">
                    {{ col.label }}
                  </b-form-checkbox>
                </b-form-checkbox-group>
              </b-form-group>
            </b-dropdown-form>
          </b-dropdown>
          <b-button :pressed.sync="showFilters" variant="outline-dark" size="sm">
            <font-awesome-icon :icon="['fas', 'filter']"/>
          </b-button>
        </b-col>
      </b-row>
    </b-container>
    <hr class="mt-0 mb-2"/>
    <b-container fluid>
      <b-row>
        <b-col style="overflow-x: auto" class="px-2">
          <stats-table
              :filteredData="filteredData"
              :displayedColumns="activeColumns"
              :tableLoading="tableLoading"
              :sortColumns="sortColumns"
              v-on:clickHeader="clickHeader"
              v-on:clickExistingPriority="clickExistingPriority"
              v-on:clickNextPriority="clickNextPriority"
              v-on:clearSortPriority="clearSortPriority"/>
        </b-col>
        <b-col cols="3" v-if="showFilters" class="pl-1 pr-2">
          <stat-filters
              :filterableColumns="filterableColumns"
              :tableData="tableData"/>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
  import Vue from 'vue'
  import axios from 'axios'
  import StatsTable from "./StatsTable";
  import StatFilters from "./StatFilters";
  
  export default {
    components: {
      StatsTable,
      StatFilters,
    },
    props: {
      statType: {type: Object},
    },
    mounted() {
      this.initialisePage(this.currentStat);
    },
    data() {
      return {
        columns: [],
        tableData: [],
        errors: [],
        tableLoading: false,
        showFilters: false,
        currentPage: 1,
        perPage: 20,
        perPageOptions: [
            {value: 20, text: 20},
            {value: 50, text: 50},
            {value: 100, text: 100},
            {value: 99999, text: 'All'}
        ],
        sortColumns: [],
        activeColumns: [],
      };
    },
    computed: {
      currentStat() {
        return this.statType.key;
      },
      optionalColumns() {
        return this.columns.filter(e => e['displayType'] === 'OPTIONAL_SHOW' || e['displayType'] === 'OPTIONAL_HIDE');
      },
      filteredData() {
        let result = this.tableData;
        const textFilterColumns = this.columns.filter(e => Object.prototype.hasOwnProperty.call(e, 'filterValue'));
        for(const column of textFilterColumns) {
          result = result.filter(e =>
              e[column['key']].toLowerCase().includes(column['filterValue'].toLowerCase()));
        }
        const minFilterColumns = this.columns.filter(e => Object.prototype.hasOwnProperty.call(e, 'filterRange'));
        for(const column of minFilterColumns) {
          result = result.filter(e => e[column['key']] >= column['filterRange']['0']);
        }
        const maxFilterColumns = this.columns.filter(e => Object.prototype.hasOwnProperty.call(e, 'filterRange'));
        for(const column of maxFilterColumns) {
          result = result.filter(e => e[column['key']] <= column['filterRange']['1']);
        }
        result.sort((a, b) => this.sortData(a, b));
        return result;
      },
      filterableColumns() {
        return this.columns.filter(e => e['filterType'] != null);
      },
    },
    watch: {
      currentStat:function(statKey) {
        this.initialisePage(statKey);
      }
    },
    methods: {
      initialisePage(statKey) {
        if(statKey != null) {
          this.tableLoading = true;
          axios.all([
            axios.get('http://localhost:9090/api/' + statKey + 'Columns',
                {auth: { username: 'user', password: 'password' }, timeout: 5000}),
            axios.get('http://localhost:9090/api/' + statKey,
                {auth: { username: 'user', password: 'password' }, timeout: 5000})
          ]).catch(error => {
            if (error.code === 'ECONNABORTED') {
              this.tableLoading = false;
              return 'timeout';
            } else {
              throw error;
            }
          }).then(responseArr => {
            this.columns = responseArr[0].data;
            this.setInitialDisplayColumns();
            this.setInitialSortColumns();
            this.tableData = responseArr[1].data;
            this.getMinMaxValues();
            this.tableLoading = false;
          });
        }
      },
      // Constructs initial state of sortColumns array
      setInitialSortColumns() {
        this.sortColumns = [];
        const col1 = this.columns.find(e => e['initialSortPriority'] === 1);
        if (col1 != null) {
          this.addNextSortColumn(col1);
          const col2 = this.columns.find(e => e['initialSortPriority'] === 2);
          if (col2 != null) {
            this.addNextSortColumn(col2);
            const col3 = this.columns.find(e => e['initialSortPriority'] === 3);
            if (col3 != null) {
              this.addNextSortColumn(col3);
            }
          }
        }
      },
      getMinMaxValues() {
        for (const column of this.columns.filter(e => e['filterType'] === 'NUMBER')) {
          const key = column['key'];
          const min = this.getMinValue(key);
          const max = this.getMaxValue(key);
          Vue.set(column, 'filterConfig', {step: 1, range: {'min': min, 'max': max}});
          Vue.set(column, 'filterRange', [min, max]);
        }
      },
      getMinValue(columnKey) {
        const values = this.tableData.map(e => e[columnKey]);
        return Math.min(...values);
      },
      getMaxValue(columnKey) {
        const values = this.tableData.map(e => e[columnKey]);
        return Math.max(...values);
      },
      setInitialDisplayColumns() {
        this.activeColumns = this.columns
            .filter(e => e['displayType'] === 'ALWAYS_SHOW' ||  e['displayType'] === 'OPTIONAL_SHOW')
            .slice();
      },
      // Compares 2 rows by priority 1, 2 then 3, and returns 1, 0 or -1 depending on result
      sortData(rowA, rowB) {
        if (this.sortColumns.length > 0) {
          const col1 = this.sortColumns[0];
          const col2 = this.sortColumns[1];
          const col3 = this.sortColumns[2];

          let result = 1;
          if (col1 != null) {
            result = this.compareByCol(col1, rowA, rowB);
          }
          if (result === 0 && col2 != null) {
            result = this.compareByCol(col2, rowA, rowB);
          }
          if (result === 0 && col3 != null) {
            result = this.compareByCol(col3, rowA, rowB);
          }
          if (result === 0) {
            result = 1;
          }
          return result;
        }
      },
      addNextSortColumn(col) {
        let position = this.sortColumns.length;
        while(position >= 3) {
          this.sortColumns.pop();
          position = this.sortColumns.length;
        }
        this.addSortColumnWithPosition(col, position);
      },
      addSortColumnWithPosition(col, position) {
        this.sortColumns.splice(position, 0, col);
        Vue.set(this.sortColumns[position], 'sortDirection', col['defaultSortDir']);
      },
      // Compares 2 rows by a specific column value
      compareByCol(col, rowA, rowB) {
        const dModifier = col['sortDirection'] === 'Asc' ? 1 : -1;
        return rowA[col.key] > rowB[col.key] ? dModifier : (rowA[col.key] === rowB[col.key] ? 0 : -dModifier);
      },
      sortDirectionClick(index) {
        if (this.sortColumns[index]['sortDirection'] === 'Asc') {
          Vue.set(this.sortColumns[index], 'sortDirection', 'Desc');
        } else {
          Vue.set(this.sortColumns[index], 'sortDirection', 'Asc');
        }
      },
      clickHeader(key) {
        if(this.sortColumns.map(e => e.key).includes(key)) {
          this.sortDirectionToggle(key);
        } else {
          this.sortByOnly(key);
        }
      },
      sortDirectionToggle(key) {
        this.sortDirectionClick(this.sortColumns.findIndex(e => e.key === key));
      },
      clearSort(index) {
        this.sortColumns.splice(index, 1);
      },
      sortByOnly(key) {
        this.sortColumns = [];
        this.addNextSortColumn(this.columns.find(e => e.key === key));
      },
      clickExistingPriority(key) {
        const col = this.sortColumns.find(e => e.key === key);
        const currentPos = this.sortColumns.findIndex(e => e.key === key);
        if(currentPos > 0) {
          this.clearSort(currentPos);
          this.addSortColumnWithPosition(col, currentPos - 1);
        }
        else{
          this.clearSort(0);
          this.addNextSortColumn(col);
        }
      },
      clickNextPriority(key) {
        this.addNextSortColumn(this.columns.find(e => e.key === key));
      },
      clearSortPriority(key) {
        this.clearSort(this.sortColumns.findIndex(e => e.key === key));
      },
    },
  };
</script>