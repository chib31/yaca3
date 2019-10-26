<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <div id="app">
    <navbar/>
    <b-container class="m-4">
      <b-form-group id="reportType" label="Please select a report: " label-cols="4">
        <b-form-select v-model="selectedReport" :options="reportOptions" v-on:change="fetchReport"/>
      </b-form-group>
      <b-form-group v-for="item in filterableColumns"
                    v-bind:key="item.id"
                    :id="item.id"
                    :label="item.label"
                    label-cols="4">
        <b-input-group v-if="item['filterable'] === 'text'">
          <b-form-select v-model="item['filterValue']" :options="Array.from(new Set(data.map(e => e[item.key])))"/>
          <b-input-group-append>
            <b-button @click="item['filterValue'] = ''">Clear</b-button>
          </b-input-group-append>
        </b-input-group>
        <b-input-group v-if="item['filterable'] === 'number'">
          <label class="mr-3">Min: {{ item['filterMin'] }}</label>
          <b-form-input v-model="item['filterMin']" type="range" :min="getMinValue(item.key)" :max="getMaxValue(item.key)"/>
          <label class="mx-3">Max: {{ item['filterMax'] }}</label>
          <b-form-input v-model="item['filterMax']" type="range" :min="getMinValue(item.key)" :max="getMaxValue(item.key)"/>
        </b-input-group>
      </b-form-group>
    </b-container>
    <stats-table v-if="data.length > 0" :columns="columns" :tableData="data" :tableLoading="tableLoading"/>
  </div>
</template>

<script>
    import StatsTable from './components/StatsTable'
    import Navbar from './components/Navbar'
    import axios from 'axios'

    export default {
        name: 'app',
        components: {
            StatsTable,
            Navbar
        },
        data() {
            return {
                isPaginated: true,
                limit: 10,
                columns: [],
                data: [],
                selectedReport: 'playerBatting',
                reportOptions: [
                    { value: null, text: 'Please select an option' },
                    { value: 'playerBatting', text: 'Individual Batting Performances' },
                    { value: 'playerBowling', text: 'Individual Bowling Performances' },
                ],
                errors: [],
                tableLoading: false,
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
               this.data = responseArr[1].data;
               this.tableLoading = false;
            });
          },
          getMinValue(column) {
            const values = this.data.map(e => e[column]);
            return Math.min(...values);
          },
          getMaxValue(column) {
            const values = this.data.map(e => e[column]);
            return Math.max(...values);
          },
        },
        computed: {
          filterableColumns() {
            return this.columns.filter(e => e['filterable'] != null);
          }
        }
    }
</script>

<style lang="scss">
  @import 'assets/custom-vars.scss';
  @import '~bootstrap-vue/src/index.scss';
  @import '~bootstrap/scss/bootstrap.scss';
</style>