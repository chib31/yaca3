<template>
  <b-container>
    <b-row>
      <b-col>
        <b-card no-body class="mt-2">
          <b-card-body class="px-2 pt-2 pb-3">
            <b-form-group
                v-for="column in filterableColumns"
                :key="column.id"
                :id="column.id"
                :label="`${column.label}:`"
                label-cols="2"
                label-size="sm"
                class="mt-2 mx-4 mb-0 p-0">
              <b-input-group v-if="column['filterType'] === 'TEXT'">
                <b-form-select
                    v-model="column['filterValue']"
                    :options="Array.from(new Set(tableData.map(e => e[column.key])))"
                    size="sm"/>
                <b-input-group-append>
                  <b-button @click="column['filterValue'] = ''" size="sm">Clear</b-button>
                </b-input-group-append>
              </b-input-group>
              <b-container v-if="column['filterType'] === 'NUMBER'" class="p-0">
                <b-row class="mx-0">
                  <b-col class="p-0 pr-1">
                    <b-input-group :prepend="`Min: ${column['filterMin']}`" size="sm">
                      <b-form-input
                          v-model.number="column['filterMin']"
                          type="range"
                          :min="column['minValue']"
                          :max="column['maxValue']"/>
                    </b-input-group>
                  </b-col>
                  <b-col class="p-0 pl-1">
                    <b-input-group :prepend="`Max: ${column['filterMax']}`" size="sm">
                      <b-form-input
                          v-model.number="column['filterMax']"
                          type="range"
                          :min="column['minValue']"
                          :max="column['maxValue']"/>
                    </b-input-group>
                  </b-col>
                  <!--                <b-col>-->
                  <!--                  <b-button @click="resetMinMaxFilter(column)" size="sm">Clear</b-button>-->
                  <!--                </b-col>-->
                </b-row>
              </b-container>
            </b-form-group>
          </b-card-body>
        </b-card>
      </b-col>
    </b-row>
  </b-container>
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