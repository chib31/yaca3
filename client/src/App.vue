<template>
  <div id="app">
    <navbar :teamName="teamName" :navOptions="navOptions" v-on:navRequest="setStatType"/>
    <stats-page v-if="this.statType.key != null" :statType="this.statType"/>
  </div>
</template>

<script>
  import StatsPage from './components/StatsPage'
  import Navbar from './components/Navbar'
    
  export default {
    name: 'app',
    components: {
      StatsPage,
      Navbar,
    },
    data() {
      return {
        teamName: 'Plastics XI',
        navOptions: [
          {'key': 'results', 'display': 'Results', 'title': 'Results',
            'icon': {'style': 'far', 'name': 'calendar-check'}},
          {'key': 'players', 'display': 'Players', 'title': 'Players',
            'icon': {'style': 'fas', 'name': 'users'}},
          {'key': 'stats', 'display': 'Stats',
            'icon': {'style': 'far', 'name': 'chart-bar'},
            'subOptions': [
                {'key': 'BATTING', 'display': 'Batting', 'title': 'Batting Stats'},
                {'key': 'BOWLING', 'display': 'Bowling', 'title': 'Bowling Stats'},
                {'key': 'FIELDING', 'display': 'Fielding', 'title': 'Fielding Stats'}]}
        ],
        statType: {type: Object},
      };
    },
    methods: {
      setStatType(statType) {
        this.statType = statType;
      }
    },
  }
</script>

<style lang="scss">
  @import 'assets/custom-vars.scss';

  @import "../node_modules/bootstrap/scss/bootstrap";
  @import '../node_modules/bootstrap-vue/src/index.scss';
</style>