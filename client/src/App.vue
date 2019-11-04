<template>
  <div id="app">
    <navbar/>
    <b-container class="mt-2">
      <b-row align-h="between">
        <b-col>
          <b-form-group label-cols="4" label-for="reportSelect" label="Select Report:" class="m-0">
            <b-form-select
                id="reportSelect"
                v-model="selectedReport"
                :options="reportOptions"
                v-on:change="fetchReport"/>
          </b-form-group>
        </b-col>
        <b-col md="auto">
          <b-button :pressed.sync="showFilters">Show Filters</b-button>
        </b-col>
      </b-row>
    </b-container>
    <stat-filters v-if="showFilters" :filterableColumns="filterableColumns" :tableData="tableData"/>
    <stats-table v-if="tableData.length > 0" :columns="columns" :tableData="tableData" :tableLoading="tableLoading"/>
  </div>
</template>

<script>
    import StatsTable from './components/StatsTable'
    import Navbar from './components/Navbar'
    import StatFilters from './components/StatFilters'
    import axios from 'axios'
    import Vue from 'vue'

    export default {
        name: 'app',
        components: {
          StatsTable,
          Navbar,
          StatFilters,
        },
        data() {
          return {
            isPaginated: true,
            limit: 10,
            columns: [],
            tableData: [],
            selectedReport: 'playerBatting',
            reportOptions: [
                { value: null, text: 'Please select an option' },
                { value: 'playerBatting', text: 'Individual Batting Performances' },
                { value: 'playerBowling', text: 'Individual Bowling Performances' },
            ],
            errors: [],
            tableLoading: false,
            showFilters: false,
          };
        },
        mounted() {
            this.fetchReport();
        },
        methods: {
          fetchReport() {
            this.tableLoading = true;
            axios.all([
                axios.get('http://localhost:9090/api/' + this.selectedReport + 'Columns',
                    {auth: { username: 'user', password: 'password' }, timeout: 5000}),
                axios.get('http://localhost:9090/api/' + this.selectedReport,
                    {auth: { username: 'user', password: 'password' }, timeout: 5000})
            ])
            .catch(error => {
              if (error.code === 'ECONNABORTED') {
                this.tableLoading = false;
                return 'timeout';
              } else {
                throw error;
              }
            })
            .then(responseArr => {
               this.columns = responseArr[0].data;
               this.tableData = responseArr[1].data;
               this.getMinMaxValues();
               this.tableLoading = false;
            });
          },
          getMinMaxValues() {
            for (const column of this.columns.filter(e => e['filterType'] === 'NUMBER')) {
              const key = column['key'];
              const min = this.getMinValue(key);
              const max = this.getMaxValue(key);
              Vue.set(column, 'minValue', min);
              Vue.set(column, 'maxValue', max);
              Vue.set(column, 'filterMin', min);
              Vue.set(column, 'filterMax', max);
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
          resetMinMaxFilter(column) {
            column['filterMin'] = column['minValue'];
            column['filterMax'] = column['maxValue'];
          },
        },
        computed: {
          filterableColumns() {
            return this.columns.filter(e => e['filterType'] != null);
          }
        }
    }
</script>

<style lang="scss">
  @import 'assets/custom-vars.scss';
  @import '~bootstrap-vue/src/index.scss';
  @import '~bootstrap/scss/bootstrap.scss';
</style>