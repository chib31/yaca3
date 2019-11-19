<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <b-container fluid class="thinBorder rounded rounded m-0 p-2">
    <b-row class="justify-content-center">
      <b-col cols="auto">
        <b-form inline>
          <label> Showing </label>
          <b-form-select v-model="perPage" :options="perPageOptions" size="sm" class="mx-1"/>
          <label> of {{ filteredDataLength }} results </label>
        </b-form>
      </b-col>
      <b-col cols="auto">
        <b-pagination v-model="currentPage"
                      v-if="paginationRequired"
                      :total-rows="filteredDataLength"
                      :per-page="perPage"
                      aria-controls="stats-table"
                      class="small m-0"/>
      </b-col>
    </b-row>
    <b-row class="mt-2">
      <b-col>
        <b-table
            id="stats-table"
            :items="filteredData"
            :fields="displayedColumns"
            :perPage="perPage"
            :currentPage="currentPage"
            :busy="tableLoading"
            thead-class="text-nowrap"
            tbody-class="text-nowrap"
            sticky-header="1000px"
            striped hover small sort-icon-left no-local-sorting no-sorting
            class="my-0 text-left">
          <template v-slot:head()="displayedColumns">
            <span class="showChildOnHover" style="margin-left: -0.4rem;">
              <b-button
                  v-if="sortColumns.some(e => e.key === displayedColumns.field.key)"
                  variant="light"
                  class="py-0 px-1"
                  style="background-color: white;">
                <font-awesome-icon
                    v-if="sortColumns.some(e => e.key === displayedColumns.field.key)"
                    :icon="['fas',
                    sortColumns.find(e => e.key === displayedColumns.field.key)['sortDirection'] === 'Asc' ?
                        'caret-up' : 'caret-down']"/>
              </b-button>
              <b-button
                  variant="light"
                  class="py-0 px-1"
                  style="font-weight: bold; background-color: white;"
                  :disabled="displayedColumns.field['sortColumn'] !== true">
                {{ displayedColumns.field.label }}
                <span
                    style="font-weight: normal"
                    v-if="sortColumns.some(e => e.key === displayedColumns.field.key) && sortColumns.length > 1">
                  [{{ sortColumns.findIndex(e => e.key === displayedColumns.field.key) + 1 }}]
                </span>
              </b-button>
<!--                <b-button-->
<!--                    v-if="displayedColumns.field['sortPriority'] > 0 && sortCols.length > 1"-->
<!--                    @click="sortPriorityClick(displayedColumns.field)"-->
<!--                    size="sm"-->
<!--                    variant="outline-dark"-->
<!--                    class="py-0 px-1 ml-1">-->
<!--                  {{ displayedColumns.field['sortPriority'] }}-->
<!--                </b-button>-->
<!--                <b-button-->
<!--                    pill-->
<!--                    v-if="displayedColumns.field['sortPriority'] > 0"-->
<!--                    size="sm"-->
<!--                    variant="outline-dark"-->
<!--                    class="py-0 px-1 ml-1">-->
<!--                  <font-awesome-icon-->
<!--                      v-if="displayedColumns.field['sortDirection'] === 'Asc'"-->
<!--                      :icon="['fas', 'arrow-up']"-->
<!--                      @click="toggleSortDirection(displayedColumns.field)"/>-->
<!--                  <font-awesome-icon-->
<!--                      v-if="displayedColumns.field['sortDirection'] === 'Desc'"-->
<!--                      :icon="['fas', 'arrow-down']"-->
<!--                      @click="toggleSortDirection(displayedColumns.field)"/>-->
<!--                </b-button>-->
<!--                <b-button-->
<!--                    v-if="displayedColumns.field.displayType === 'OPTIONAL_SHOW'"-->
<!--                    size="sm" variant="light" class="p-0 showOnHover ml-1" pill>-->
<!--                  <font-awesome-icon :icon="['far', 'times-circle']"/>-->
<!--                </b-button>-->
          </span>
          </template>
          <template v-slot:table-busy>
            <div class="text-center text-danger my-2">
              <b-spinner class="align-middle"></b-spinner>
              <strong>Loading...</strong>
            </div>
          </template>
          <template v-slot:cell(index)="filteredData">
            {{ (filteredData.index + 1) + (perPage * (currentPage - 1)) }}
          </template>
        </b-table>
      </b-col>
    </b-row>
    <b-row class="justify-content-center mt-2">
      <b-col cols="auto">
        <b-form inline>
          <label> Showing </label>
          <b-form-select v-model="perPage" :options="perPageOptions" size="sm" class="mx-1"/>
          <label> of {{ filteredDataLength }} results </label>
        </b-form>
      </b-col>
      <b-col cols="auto">
        <b-pagination v-model="currentPage"
                      v-if="paginationRequired"
                      :total-rows="filteredDataLength"
                      :per-page="perPage"
                      aria-controls="stats-table"
                      class="small m-0"/>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
  export default {
    name: "StatsTable",
    props: {
      filteredData: {type: Array},
      displayedColumns: {type: Array},
      tableLoading: {type: Boolean, default: false},
      sortColumns: {type: Array},
    },
    data() {
      return {
        currentPage: 1,
        perPage: 20,
        perPageOptions: [
          {value: 20, text: 20},
          {value: 50, text: 50},
          {value: 100, text: 100},
          {value: 99999, text: 'All'}
        ],
      };
    },
    computed: {
      filteredDataLength() {
        return this.filteredData.length;
      },
      paginationRequired() {
        return this.filteredDataLength > this.perPage;
      },
    }
  }
</script>

<style scoped>

</style>