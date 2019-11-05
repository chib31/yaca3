<template>
  <div class="border border-secondary rounded p-1 text-left">
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
        <b-input-group :prepend="`Min: ${column['filterMin']}`" size="sm">
          <b-form-input
              v-model.number="column['filterMin']"
              type="range"
              :min="column['minValue']"
              :max="column['maxValue']"/>
        </b-input-group>
        <b-input-group :prepend="`Max: ${column['filterMax']}`" size="sm">
          <b-form-input
              v-model.number="column['filterMax']"
              type="range"
              :min="column['minValue']"
              :max="column['maxValue']"/>
        </b-input-group>
      </div>
    </b-form-group>
  </div>
</template>

<script>
  export default {
    name: "StatFilters",
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