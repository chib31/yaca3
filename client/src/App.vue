<template>
  <div id="app">
    <navbar :teamName="teamName" :navOptions="navOptions" v-on:navRequest="fetchReport"/>
    <b-container class="mx-0 mt-4">
      <b-row>
        <b-col cols="auto">
          <h1>
            <span>{{ this.currentTitle }}</span>
            <b-button :pressed.sync="showFilters" variant="outline-dark" class="ml-3 mb-2">
              <font-awesome-icon :icon="['fas', 'filter']"/>
            </b-button>
          </h1>
        </b-col>
      </b-row>
    </b-container>
    <hr class="my-2"/>
    <b-container fluid>
      <b-row>
        <b-col cols="3" v-if="showFilters" class="pr-1 pl-2">
          <stat-filters
              :filterableColumns="filterableColumns"
              :tableData="tableData"/>
        </b-col>
        <b-col style="overflow-x: auto" class="pl-1 pr-2">
          <stats-table
              v-if="tableData.length > 0"
              :columns="columns"
              :tableData="tableData"
              :tableLoading="tableLoading"/>
        </b-col>
      </b-row>
    </b-container>
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
            reportOptions: [
                { value: null, text: 'Please select an option' },
                { value: 'playerBatting', text: 'Individual Batting Performances' },
                { value: 'playerBowling', text: 'Individual Bowling Performances' },
            ],
            errors: [],
            tableLoading: false,
            showFilters: false,
            teamName: 'Plastics XI',
            navOptions: [
              {'key': 'results', 'display': 'Results', 'title': 'Results',
                'icon': {'style': 'far', 'name': 'calendar-check'}},
              {'key': 'players', 'display': 'Players', 'title': 'Players',
                'icon': {'style': 'fas', 'name': 'users'}},
              {'key': 'stats', 'display': 'Stats',
                'icon': {'style': 'far', 'name': 'chart-bar'},
                'subOptions': [
                    {'key': 'batting', 'display': 'Batting', 'title': 'Batting Stats'},
                    {'key': 'bowling', 'display': 'Bowling', 'title': 'Bowling Stats'},
                    {'key': 'fielding', 'display': 'Fielding', 'title': 'Fielding Stats'}]}
            ],
            currentTitle: 'Batting Stats',
          };
        },
        methods: {
          fetchReport(navOption) {
            this.tableLoading = true;
            this.currentTitle = navOption.title;
            const report = navOption.key;
            axios.all([
                axios.get('http://localhost:9090/api/' + report + 'Columns',
                    {auth: { username: 'user', password: 'password' }, timeout: 5000}),
                axios.get('http://localhost:9090/api/' + report,
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
        },
        computed: {
          filterableColumns() {
            return this.columns.filter(e => e['filterType'] != null);
          },
        }
    }
</script>

<style lang="scss">
  @import 'assets/custom-vars.scss';

  @import "../node_modules/bootstrap/scss/bootstrap";
  @import '../node_modules/bootstrap-vue/src/index.scss';
</style>