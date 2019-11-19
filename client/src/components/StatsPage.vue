<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <div>
    <b-container fluid id="container-stats-page">
      <b-row class="my-2">
        <b-col cols="auto" class="py-0 my-0 mt-auto">
          <h1 class="align-text-bottom py-0 my-0">
            <span class="align-text-bottom">{{ this.statType.title }}</span>
          </h1>
        </b-col>
        <b-col cols="auto" class="mt-auto">
          <span>Sorting by:</span>
          <draggable
              v-model="sortColumns"
              group="this_is_group"
              @start="drag=true"
              @end="drag=false"
              style="display: inline-block">
            <span
                v-for="(col, index) in sortColumns"
                :key="col.key"
                class="dragBox pl-2 visibleChildOnHover">
              <span> {{ index + 1 }}-{{ col.label}} </span>
              <b-button
                  pill
                  @click="sortDirectionClick(index)"
                  variant="light"
                  style="display: inline-block"
                  class="px-1 py-0"
                  size="sm">
                <font-awesome-icon :icon="['fas', col.sortDirection === 'Asc' ? 'caret-up' : 'caret-down']"/>
              </b-button>
              <span>
                <b-button
                    pill
                    @click="clearSortClick(index)"
                    variant="light"
                    class="mr-1 p-0 visibleOnHover"
                    size="sm">
                  <font-awesome-icon :icon="['fas', 'times']"/>
                </b-button>
              </span>
            </span>
          </draggable>
          <b-dropdown
              no-caret
              variant="light"
              id="button-add-sort"
              size="sm"
              class="ml-1"
              :disabled="sortColumns.length >= 3">
            <template v-slot:button-content disabled="true">
              <font-awesome-icon :icon="['fas', 'plus']"/>
            </template>
            <b-dropdown-item
                v-for="col in sortableColumns"
                :key="col.key"
                :disabled="sortColumns.some(e => e.key === col.key)"
                @click="addSortColumn(col)">
              {{ col.label }}
            </b-dropdown-item>
          </b-dropdown>
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
          <b-dropdown id="sortByDropdown" text="Sort By" variant="outline-dark" size="sm" class="mr-2">
            <b-dropdown-item v-for="column in sortableColumns" :key="column['key']">
              {{ column['label'] }}
            </b-dropdown-item>
            <b-dropdown-divider/>
            <b-dropdown-item>Reset</b-dropdown-item>
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
              :sortColumns="sortColumns"/>
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
  import draggable from 'vuedraggable';
  
  export default {
    components: {
      StatsTable,
      StatFilters,
      draggable
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
      sortableColumns() {
        return this.columns.filter(e => e['sortColumn'] === true);
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
      // sortCols() {
      //   let result = this.columns.filter(e => e['sortPriority'] > 0);
      //   result.sort((a, b) => a['sortPriority'] > b['sortPriority'] ? 1 : -1);
      //   return result;
      // },
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
          this.addSortColumn(col1);
          const col2 = this.columns.find(e => e['initialSortPriority'] === 2);
          if (col2 != null) {
            this.addSortColumn(col2);
            const col3 = this.columns.find(e => e['initialSortPriority'] === 3);
            if (col3 != null) {
              this.addSortColumn(col3);
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
      addSortColumn(col) {
        const sortColCount = this.sortColumns.length;
        if(sortColCount >= 3) {
          this.sortColumns.pop();
        }
        this.sortColumns.push(col);
        Vue.set(this.sortColumns[this.sortColumns.length - 1], 'sortDirection', col['defaultSortDir']);
      },
      // Compares 2 rows by a specific column value
      compareByCol(col, rowA, rowB) {
        const dModifier = col['sortDirection'] === 'Asc' ? 1 : -1;
        return rowA[col.key] > rowB[col.key] ? dModifier : (rowA[col.key] === rowB[col.key] ? 0 : -dModifier);
      },
      // togglePriorityClick(col) {
      //   let thisCol = this.columns.find(e => e['key'] === col['key']);
      //   const pInitial = Object.prototype.hasOwnProperty.call(thisCol, 'sortPriority') ? thisCol['sortPriority'] : 0;
      //   if (pInitial === 0) {
      //     // Column does not yet have a priority - if there is an available slot take it, otherwise replace p3
      //     const nextPriority = this.sortCols.length + 1;
      //     if (nextPriority > 3) {
      //       this.columns.filter(e => e['sortPriority'] > 2).map(e => delete e['sortPriority']);
      //       Vue.set(thisCol, 'sortPriority', 3);
      //     } else {
      //       Vue.set(thisCol, 'sortPriority', nextPriority);
      //     }
      //     Vue.set(thisCol, 'sortDirection', 'Asc');
      //   } else if (pInitial === 1) {
      //     // Column is already p1: delete its priority and move everything else down 1
      //     this.columns.filter(e => e['sortPriority'] > 0).map(e => e['sortPriority'] = e['sortPriority'] - 1);
      //     delete thisCol['sortPriority'];
      //   } else {
      //     // Column is already p2 / p3: swap it with the next highest slot
      //     const pTarget = pInitial - 1;
      //     let swapCol = this.columns.find(e => e['sortPriority'] === pTarget);
      //     Vue.set(swapCol, 'sortPriority', pInitial);
      //     Vue.set(thisCol, 'sortPriority', pTarget);
      //   }
      // },
      sortDirectionClick(index) {
        if (this.sortColumns[index]['sortDirection'] === 'Asc') {
          Vue.set(this.sortColumns[index], 'sortDirection', 'Desc');
        } else {
          Vue.set(this.sortColumns[index], 'sortDirection', 'Asc');
        }
      },
      clearSortClick(index) {
        this.sortColumns.splice(index, 1);
      }
    },
  };
</script>