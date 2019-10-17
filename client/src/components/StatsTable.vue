<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <b-container fluid>
    <hr/>
    <b-row class="justify-content-md-center mb-1">
      <b-col md="auto">
        <b-form inline>
          <label> Showing </label>
          <b-form-select v-model="perPage" :options="perPageOptions" size="sm" class="mx-1"/>
          <label> of {{ filteredDataLength }} results </label>
        </b-form>
      </b-col>
    </b-row>
    <b-row class="justify-content-md-center">
      <b-col md="auto">
        <b-pagination v-model="currentPage"
                      :total-rows="filteredDataLength"
                      :per-page="perPage"
                      aria-controls="stats-table"
                      class="small"/>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <b-table id="stats-table"
                 :items="filteredData"
                 :fields="fields"
                 :perPage="perPage"
                 :currentPage="currentPage"
                 style="text-align: left;"
                 sticky-header="1000px"
                 striped hover small sort-icon-left>
          <template v-slot:cell(index)="filteredData">
            {{ (filteredData.index + 1) + (perPage * (currentPage - 1)) }}
          </template>
          <template v-slot:cell(strikeRate)="filteredData">
            {{ filteredData.value.toFixed(2) }}
          </template>
          <template v-slot:cell(percentOfTotal)="filteredData">
            {{ filteredData.value.toFixed(2) }}%
          </template>
        </b-table>
      </b-col>
    </b-row>
    <b-row class="justify-content-md-center">
      <b-col md="auto">
        <b-pagination v-model="currentPage"
                      :total-rows="filteredDataLength"
                      :per-page="perPage"
                      aria-controls="stats-table"
                      class="small"/>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
    
    export default {
        props: {
            data: {
                type: Array,
                required: true,
            },
            columns: {
                type: Array,
                required: true,
            },
            searchQuery: {
                type: String,
                default: null,
            },
            isPaginated: {
                type: Boolean,
                default: false,
            },
            initSortKey: {
                type: String,
                default: null,
            },
        },
        data() {
            let initSortOrders = {};
            initSortOrders = this.columns.reduce((obj, key) => {
                const acc = Object.assign({}, obj);
                if (this.initSortKey === key) {
                    acc[key] = -1;
                } else {
                    acc[key] = 1;
                }
                return acc;
            }, {});
            return {
                sortKey: this.initSortKey,
                sortOrders: initSortOrders,
                currentPage: 1,
                filterKey: this.searchQuery,
                perPage: 20,
                perPageOptions: [
                    {value: 20, text: 20},
                    {value: 50, text: 50},
                    {value: 100, text: 100},
                    {value: 'all', text: 'All'}
                ],
                fields: [
                  { key: 'index', label: '#' },
                  { key: 'playerName', sortable: true },
                  { key: 'runs', sortable: true },
                  { key: 'deliveries', sortable: true },
                  { key: 'fours', sortable: true },
                  { key: 'sixes', sortable: true },
                  { key: 'wicketType', label: 'Wicket', sortable: true },
                  { key: 'strikeRate', label: 'S/R', sortable: true },
                  { key: 'percentOfTotal', label: '% of Total', sortable: true },
                  { key: 'position', sortable: true },
                  { key: 'fixture', sortable: true },
                ]
            };
        },
        computed: {
            filteredData() {
                const filterKey = this.filterKey && this.filterKey.toLowerCase();
                let { data } = this;
                if (filterKey) {
                    data = data.filter(row =>
                        Object.keys(row).some((key) => {
                            const value = String(row[key]);
                            return value.toLowerCase().indexOf(filterKey) !== -1;
                        }));
                }
                if (this.sortKey) {
                    data = data.slice().sort((a, b) => {
                        const aValue = a[this.sortKey];
                        const bValue = b[this.sortKey];
                        return this.sortValues(aValue, bValue) * this.sortOrders[this.sortKey];
                    });
                }
                return data;
            },
            filteredDataLength() {
                return this.filteredData.length;
            },
            displayedData() {
                const dataIndex = (this.currentPage - 1) * this.limit;
                return this.filteredData.slice(dataIndex, dataIndex + this.limit);
            },
            displayedDataLength() {
                return this.displayedData.length;
            },
            isButtonsDisplayed() {
                return this.initLimit < this.displayedDataLength
                    || this.initLimit < this.filteredDataLength;
            },
        },
        watch: {
            initLimit(newValue) {
                this.limit = newValue;
            },
            isPaginated() {
                this.limit = this.initLimit;
            },
        },
        methods: {
            sortBy(key) {
                this.sortKey = key;
                this.sortOrders[key] = this.sortOrders[key] * -1;
            },
            sortValues(a, b) {
                if (a > b) {
                    return 1;
                } else if (a < b) {
                    return -1;
                }
                return 0;
            },
            setLimit(limit) {
                this.limit = limit;
            },
        },
    };
</script>

<!--<style scoped lang="scss">-->
<!--  .container {-->
<!--    display: flex;-->
<!--    flex-direction: column;-->
<!--    align-items: center;-->
<!--    .search {-->
<!--      margin-bottom: 10px;-->
<!--      margin-right: 10px;-->
<!--    }-->
<!--    table {-->
<!--      border-collapse: separate;-->
<!--      border-spacing: 2px;-->
<!--      border-radius: 3px;-->
<!--      background-color: white;-->
<!--      margin: 10px;-->
<!--      &.table-striped > tbody > tr:nth-child(even) > td,-->
<!--      .table-striped > tbody > tr:nth-child(even) > th {-->
<!--        background-color: #f9f9f9;-->
<!--      }-->
<!--      &.table-striped > tbody > tr:nth-child(odd) > td,-->
<!--      .table-striped > tbody > tr:nth-child(odd) > th {-->
<!--        background-color: white;-->
<!--      }-->
<!--      th {-->
<!--        padding: 8px;-->
<!--        background-color: #f9f9f9;-->
<!--        color: rgba(76, 89, 90, 0.66);-->
<!--        cursor: pointer;-->
<!--        &.active {-->
<!--          color: #4C595A;-->
<!--          & .arrow {-->
<!--            opacity: 1;-->
<!--          }-->
<!--        }-->
<!--      }-->
<!--      .arrow {-->
<!--        display: inline-block;-->
<!--        vertical-align: middle;-->
<!--        width: 0;-->
<!--        height: 0;-->
<!--        margin-left: 5px;-->
<!--        opacity: 0.66;-->
<!--        &.asc {-->
<!--          border-left: 4px solid transparent;-->
<!--          border-right: 4px solid transparent;-->
<!--          border-bottom: 4px solid rgba(76, 89, 90, 0.66);;-->
<!--        }-->
<!--        &.dsc {-->
<!--          border-left: 4px solid transparent;-->
<!--          border-right: 4px solid transparent;-->
<!--          border-top: 4px solid rgba(76, 89, 90, 0.66);;-->
<!--        }-->
<!--      }-->
<!--      td {-->
<!--        padding: 8px;-->
<!--      }-->
<!--    }-->
<!--  }-->
<!--</style>-->