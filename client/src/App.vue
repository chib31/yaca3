<template>
  <div id="app">
    <navbar/>
    <b-container class="m-4">
      <b-form>
        <b-form-group id="reportType" label="Please select a report: " label-cols="4">
          <b-form-select v-model="selectedReport" :options="reportOptions" v-on:change="fetchReport"/>
        </b-form-group>
      </b-form>
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
            console.log(this.selectedReport);
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
          }
        }
    }
</script>

<style lang="scss">
  @import 'assets/custom-vars.scss';
  @import '~bootstrap-vue/src/index.scss';
  @import '~bootstrap/scss/bootstrap.scss';
</style>