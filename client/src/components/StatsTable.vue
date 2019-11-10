<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <div>
    <b-container fluid class="thinBorder rounded rounded m-0 p-2">
      <b-row class="justify-content-center">
        <b-col>
          <b-dropdown
              id="addColumnsDropdown"
              text="Add Columns"
              :disabled="hiddenColumns.length < 1"
              variant="outline-dark">
            <b-dropdown-item v-for="column in hiddenColumns" :key="column['key']" v-on:click="showColumn(column)">
              {{ column['label'] }}
            </b-dropdown-item>
            <b-dropdown-divider/>
            <b-dropdown-item>Reset</b-dropdown-item>
          </b-dropdown>
        </b-col>
        <b-col>
          <b-dropdown
              id="sortByDropdown"
              text="Sort By"
              variant="outline-dark">
            <b-dropdown-item v-for="column in sortableColumns" :key="column['key']">
              {{ column['label'] }}
            </b-dropdown-item>
            <b-dropdown-divider/>
            <b-dropdown-item>Reset</b-dropdown-item>
          </b-dropdown>
        </b-col>
        <b-col cols="auto">
          <b-form inline>
            <label> Showing </label>
            <b-form-select v-model="perPage" :options="perPageOptions" size="sm" class="mx-1"/>
            <label> of {{ dataLength }} results </label>
          </b-form>
        </b-col>
        <b-col cols="auto">
          <b-pagination v-model="currentPage"
                        v-if="paginationRequired"
                        :total-rows="dataLength"
                        :per-page="perPage"
                        aria-controls="stats-table"
                        class="small m-0"/>
        </b-col>
      </b-row>
      <b-row>
        <b-col>
          Sorting by:
          <span v-for="col in this.sortCols" :key="col['sortPriority']" class="thinBorder rounded p-1">
            {{ col.label }} ({{ col.sortDirection }})
            <b-button
                size="sm" variant="light" class="p-0" pill>
              <font-awesome-icon :icon="['far', 'times-circle']"/>
            </b-button>
          </span>
        </b-col>
      </b-row>
      <b-row class="mt-2">
        <b-col>
          <b-table
              id="stats-table"
              :items="filteredData"
              :fields="displayColumns"
              :perPage="perPage"
              :currentPage="currentPage"
              :busy="tableLoading"
              thead-class="text-nowrap"
              tbody-class="text-nowrap"
              sticky-header="1000px"
              striped hover small sort-icon-left no-local-sorting no-sorting
              class="my-0 text-left">
            <template v-slot:head()="displayColumns">
              <span class="showChildOnHover">
                <button
                    class="headerLink"
                    :disabled="displayColumns.field['sortColumn'] !== true"
                    @click="sortPriorityClick(displayColumns.field)">
                  {{ displayColumns.field.label }}
                </button>
                <b-button
                    v-if="displayColumns.field['sortPriority'] > 0 && sortCols.length > 1"
                    @click="sortPriorityClick(displayColumns.field)"
                    size="sm"
                    variant="outline-dark"
                    class="py-0 px-1 ml-1">
                  {{ displayColumns.field['sortPriority'] }}
                </b-button>
                <b-button
                    pill
                    v-if="displayColumns.field['sortPriority'] > 0"
                    size="sm"
                    variant="outline-dark"
                    class="py-0 px-1 ml-1">
                  <font-awesome-icon
                      v-if="displayColumns.field['sortDirection'] === 'Asc'"
                      :icon="['fas', 'arrow-up']"
                      @click="toggleSortDirection(displayColumns.field)"/>
                  <font-awesome-icon
                      v-if="displayColumns.field['sortDirection'] === 'Desc'"
                      :icon="['fas', 'arrow-down']"
                      @click="toggleSortDirection(displayColumns.field)"/>
                </b-button>
                <b-button
                    v-if="displayColumns.field.displayType === 'OPTIONAL_SHOW'"
                    @click="hideColumn(displayColumns.field.key)"
                    size="sm" variant="light" class="p-0 showOnHover ml-1" pill>
                  <font-awesome-icon :icon="['far', 'times-circle']"/>
                </b-button>
              </span>
            </template>
            <template v-slot:table-busy>
              <div class="text-center text-danger my-2">
                <b-spinner class="align-middle"></b-spinner>
                <strong>Loading...</strong>
              </div>
            </template>
            <template v-slot:cell(index)="filteredData">
              {{ (filteredData.index + 1) + (perPage * (currentPage - 1)) }}
            </template>
            <template v-slot:cell(strikeRate)="filteredData">
              {{ filteredData.value === '' ? 'n/a' : filteredData.value.toFixed(1) }}
            </template>
            <template v-slot:cell(average)="filteredData">
              {{ filteredData.value === '' ? 'n/a' : filteredData.value.toFixed(1) }}
            </template>
            <template v-slot:cell(percentOfTotal)="filteredData">
              {{ filteredData.value === '' ? 'n/a' : filteredData.value.toFixed(1) + '%' }}
            </template>
            <template v-slot:cell(economy)="filteredData">
              {{ filteredData.value === '' ? 'n/a' : filteredData.value.toFixed(2) }}
            </template>
            <template v-slot:cell(playerName)="filteredData">
              {{ filteredData.value }}
            </template>
            <template v-slot:cell(fixture)="filteredData">
              {{ filteredData.value }}
            </template>
          </b-table>
        </b-col>
      </b-row>
      <b-row class="justify-content-center mt-2">
        <b-col cols="auto">
          <b-form inline>
            <label> Showing </label>
            <b-form-select v-model="perPage" :options="perPageOptions" size="sm" class="mx-1"/>
            <label> of {{ dataLength }} results </label>
          </b-form>
        </b-col>
        <b-col cols="auto">
          <b-pagination v-model="currentPage"
                        v-if="paginationRequired"
                        :total-rows="dataLength"
                        :per-page="perPage"
                        aria-controls="stats-table"
                        class="small m-0"/>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
  import Vue from 'vue'
  export default {
    props: {
      tableData: {
        type: Array,
        required: true,
      },
      columns: {
        type: Array,
        required: true,
      },
        tableLoading: Boolean,
      },
      data() {
        return {
          sortBy: 'age',
          sortDesc: false,
          currentPage: 1,
          perPage: 20,
          perPageOptions: [
              {value: 20, text: 20},
              {value: 50, text: 50},
              {value: 100, text: 100},
              {value: 99999, text: 'All'}
          ],
          sortColumns: Array,
        };
      },
      computed: {
        dataLength() {
          return this.filteredData.length;
        },
        displayColumns() {
          return this.columns.filter(e => e['displayType'] === 'ALWAYS_SHOW' || e['displayType'] === 'OPTIONAL_SHOW');
        },
        hiddenColumns() {
          return this.columns.filter(e => e['displayType'] === 'OPTIONAL_HIDE');
        },
        sortableColumns() {
          return this.columns.filter(e => e['sortable'] === true);
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
        paginationRequired() {
          return this.dataLength > this.perPage;
        },
        sortCol1() {
          return this.sortCols.find(e => e['sortPriority'] === 1);
        },
        sortCol2() {
          return this.sortCols.find(e => e['sortPriority'] === 2);
        },
        sortCol3() {
          return this.sortCols.find(e => e['sortPriority'] === 3);
        },
        sortCols() {
          let result = this.columns.filter(e => e['sortPriority'] > 0);
          result.sort((a, b) => a['sortPriority'] > b['sortPriority'] ? 1 : -1);
          return result;
        }
      },
      mounted() {
        this.setInitialSortCols();
      },
      methods: {
        sortData(rowA, rowB) {
          const col1 = this.sortCol1;
          const col2 = this.sortCol2;
          const col3 = this.sortCol3;
          
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
        },
        compareByCol(col, rowA, rowB) {
          const dModifier = col['sortDirection'] === 'Asc' ? 1 : -1;
          return rowA[col.key] > rowB[col.key] ? dModifier : (rowA[col.key] === rowB[col.key] ? 0 : -dModifier);
        },
        showColumn(column) {
          column['displayType'] = 'OPTIONAL_SHOW';
        },
        hideColumn(columnKey) {
          console.log(columnKey);
          const column = this.columns.find(e => e.key === columnKey);
          column['displayType'] = 'OPTIONAL_HIDE';
        },
        setInitialSortCols() {
          let result = this.sortableColumns.filter(e => e['sortPriority'] > 0);
          result.sort((a, b) => a['sortPriority'] > b['sortPriority'] ? 1 : -1);
          this.sortColumns = result;
        },
        sortPriorityClick(col) {
          let thisCol = this.columns.find(e => e['key'] === col['key']);
          const pInitial = Object.prototype.hasOwnProperty.call(thisCol, 'sortPriority') ? thisCol['sortPriority'] : 0;
          if (pInitial === 0) {
            // Column does not yet have a priority - if there is an available slot take it, otherwise replace p3
            const nextPriority = this.sortCols.length + 1;
            if (nextPriority > 3) {
              this.columns.filter(e => e['sortPriority'] > 2).map(e => delete e['sortPriority']);
              Vue.set(thisCol, 'sortPriority', 3);
            } else {
              Vue.set(thisCol, 'sortPriority', nextPriority);
            }
            Vue.set(thisCol, 'sortDirection', 'Asc');
          } else if (pInitial === 1) {
            // Column is already p1: delete its priority and move everything else down 1
            this.columns.filter(e => e['sortPriority'] > 0).map(e => e['sortPriority'] = e['sortPriority'] - 1);
            delete thisCol['sortPriority'];
          } else {
            // Column is already p2 / p3: swap it with the next highest slot
            const pTarget = pInitial - 1;
            let swapCol = this.columns.find(e => e['sortPriority'] === pTarget);
            Vue.set(swapCol, 'sortPriority', pInitial);
            Vue.set(thisCol, 'sortPriority', pTarget);
          }
        },
        toggleSortDirection(col) {
          let thisCol = this.columns.find(e => e['key'] === col['key']);
          if (thisCol['sortDirection'] === 'Asc') {
            thisCol['sortDirection'] = 'Desc';
          } else {
            thisCol['sortDirection'] = 'Asc';
          }
        }
      }
    };
</script>