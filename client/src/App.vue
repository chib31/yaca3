<template>
  <div id="app">
    <navbar/>
    <b-container class="m-4">
      <b-form-row class="mb-4">
        <b-col cols="4">
          Report:
        </b-col>
        <b-col cols="8">
          <b-form-select v-model="selectedReport" :options="reportOptions"></b-form-select>
        </b-col>
      </b-form-row>
      <b-row class="mb-4">
        <b-col>
          <b-button size="m" class="btn-primary" type="submit" @click.prevent="fetchReport">Submit</b-button>
        </b-col>
      </b-row>
    </b-container>
    <stats-table v-if="data.length > 0" :columns="columns" :data="data" :init-limit="limit" :is-paginated="isPaginated"
                 :init-sort-key="columns[0].key"/>
  </div>
</template>

<script>
    import StatsTable from './components/StatsTable'
    import Navbar from './components/Navbar'
    import {AXIOS} from "./http-commons"

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
                ]
            };
        },
        mounted() {
            this.fetchReport()
        },
        methods: {
            fetchReport() {
                AXIOS.get('http://localhost:9090/api/' + this.selectedReport, {auth: {username: 'user', password: 'password'}})
                    .then(response => {this.data = response.data; this.columns = Object.keys(response.data[0]);})
                    .catch(e => {this.errors.push(e)})
            }
        }
    }
</script>

<style lang="scss">
  @import 'assets/custom-vars.scss';
  @import '~bootstrap-vue/src/index.scss';
  @import '~bootstrap/scss/bootstrap.scss';
</style>