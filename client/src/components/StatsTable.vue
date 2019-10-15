<template>
  <b-container fluid>
    <hr/>
    <!--    <b-form-input v-model="filterKey" placeholder="Search..." class="search"/>-->
    <b-row align-h="center" class="mb-2">
      <b-col align-h="right">
        <b-pagination v-if="isPaginated" :total-rows="filteredDataLength" v-model="currentPage" :per-page="limit"
                      type="dark" align="right"/>
      </b-col>
      <b-col align-h="left">
        <b-form inline>
          <label> Showing </label>
          <b-form-select v-model="limit" :options="perPageOptions" size="sm" class="mx-1"/>
          <label> of {{ filteredDataLength }} results </label>
        </b-form>
      </b-col>
    </b-row>
    <b-table striped hover small sticky-header="1000px" :items="displayedData"/>
    <b-row align-h="center" class="mb-2">
      <b-col align-h="right">
        <b-pagination v-if="isPaginated" :total-rows="filteredDataLength" v-model="currentPage" :per-page="limit"
                      type="dark" align="right"/>
      </b-col>
      <b-col align-h="left">
        <b-form inline>
          <label> Showing </label>
          <b-form-select v-model="limit" :options="perPageOptions" size="sm" class="mx-1"/>
          <label> of {{ filteredDataLength }} results </label>
        </b-form>
      </b-col>
    </b-row>
    <hr/>
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
            initLimit: {
                type: Number,
                default: 5,
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
                limit: this.initLimit,
                perPageOptions: [
                    {value: 10, text: 10},
                    {value: 25, text: 25},
                    {value: 50, text: 50},
                    {value: 100, text: 100},
                    {value: 9999, text: 'All'}
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