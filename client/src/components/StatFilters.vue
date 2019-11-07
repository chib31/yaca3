<template>
  <div class="thinBorder rounded p-1 text-left">
    <h3>Filters</h3>
    <b-form-group
        v-for="column in filterableColumns"
        :key="column.id"
        :id="column.id"
        :label="`${column.label}:`"
        class="mt-2" border-primary>
      <b-input-group v-if="column['filterType'] === 'TEXT'">
        <b-form-select
            v-model="column['filterValue']"
            :options="Array.from(new Set(tableData.map(e => e[column.key])))"
            size="sm"/>
        <b-input-group-append>
          <b-button @click="column['filterValue'] = ''" size="sm">Clear</b-button>
        </b-input-group-append>
      </b-input-group>
      <div v-if="column['filterType'] === 'NUMBER'" class="m-0">
        <b-container>
          <b-row>
            <b-col cols="2">
              <span class="text-right">{{ Math.round(column['filterRange']['0']) }}</span>
            </b-col>
            <b-col>
              <nouislider
                  :config="column['filterConfig']"
                  :values="column['filterRange']"
                  @update="column['filterRange']"/>
            </b-col>
            <b-col cols="2">
              <span class="text-left">{{ Math.round(column['filterRange']['1']) }}</span>
            </b-col>
          </b-row>
        </b-container>
      </div>
    </b-form-group>
  </div>
</template>

<script>
  import Nouislider from 'vue-nouislider/src/components/noUiSlider';
  
  export default {
    name: "StatFilters",
    components: {
      Nouislider,
    },
    props: {
      filterableColumns: {
        type: Array,
        required: true,
      },
      tableData: {
        type: Array,
        required: true,
      }
    },
  }
</script>