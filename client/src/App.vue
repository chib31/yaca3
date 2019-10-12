<template>
  <div id="app">
    <img alt="Plastics logo" src="./assets/PlasticsLogoText.png" width="128">
    <stats-table :columns="columns" :data="data" :init-limit="limit" :is-paginated="isPaginated"
                 :init-sort-key="columns[0].key"/>
  </div>
</template>

<script>
    import StatsTable from './components/StatsTable'
    import {AXIOS} from "./http-commons";

    export default {
        name: 'app',
        components: {
            StatsTable,
            // Reports
        },
        data() {
            return {
                isPaginated: true,
                limit: 10,
                columns: [],
                data: [],
            };
        },
        mounted() {
            AXIOS.get(`http://localhost:9090/api/playerBatting`, {auth: {username: 'user', password: 'password'}})
                .then(response => {this.data = response.data; this.columns = Object.keys(response.data[0]);})
                .catch(e => {this.errors.push(e)})
        },
    }
</script>

<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
  }
</style>