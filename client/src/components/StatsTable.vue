<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <div>
    <b-container fluid class="thinBorder rounded rounded m-0 p-2">
      <b-row class="justify-content-center">
        <b-col cols="auto">
          <b-form inline>
            <label> Showing </label>
            <b-form-select v-model="perPage" :options="perPageOptions" size="sm" class="mx-1"/>
            <label> of {{ dataLength }} results </label>
          </b-form>
        </b-col>
        <b-col cols="auto">
          <b-pagination v-model="currentPage"
                        v-if="paginationRequired"
                        :total-rows="dataLength"
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
              :fields="columns"
              :perPage="perPage"
              :currentPage="currentPage"
              :busy="tableLoading"
              thead-class="text-nowrap"
              tbody-class="text-nowrap"
              sticky-header="1000px"
              striped hover small sort-icon-left
              class="my-0 text-left">
            <template v-slot:table-busy>
              <div class="text-center text-danger my-2">
                <b-spinner class="align-middle"></b-spinner>
                <strong>Loading...</strong>
              </div>
            </template>
            <template v-slot:cell(index)="filteredData">
              {{ (filteredData.index + 1) + (perPage * (currentPage - 1)) }}
            </template>
            <template v-slot:cell(strikeRate)="filteredData">
              {{ filteredData.value === '' ? 'n/a' : filteredData.value.toFixed(1) }}
            </template>
            <template v-slot:cell(average)="filteredData">
              {{ filteredData.value === '' ? 'n/a' : filteredData.value.toFixed(1) }}
            </template>
            <template v-slot:cell(percentOfTotal)="filteredData">
              {{ filteredData.value === '' ? 'n/a' : filteredData.value.toFixed(1) + '%' }}
            </template>
            <template v-slot:cell(economy)="filteredData">
              {{ filteredData.value === '' ? 'n/a' : filteredData.value.toFixed(2) }}
            </template>
            <template v-slot:cell(playerName)="filteredData">
              {{ filteredData.value }}
            </template>
            <template v-slot:cell(fixture)="filteredData">
              {{ filteredData.value }}
            </template>
          </b-table>
        </b-col>
      </b-row>
      <b-row class="justify-content-center mt-2">
        <b-col cols="auto">
          <b-form inline>
            <label> Showing </label>
            <b-form-select v-model="perPage" :options="perPageOptions" size="sm" class="mx-1"/>
            <label> of {{ dataLength }} results </label>
          </b-form>
        </b-col>
        <b-col cols="auto">
          <b-pagination v-model="currentPage"
                        v-if="paginationRequired"
                        :total-rows="dataLength"
                        :per-page="perPage"
                        aria-controls="stats-table"
                        class="small m-0"/>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
    export default {
        props: {
            tableData: {
                type: Array,
                required: true,
            },
            columns: {
                type: Array,
                required: true,
            },
            tableLoading: Boolean,
        },
        data() {
            return {
                sortBy: 'age',
                sortDesc: false,
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
          dataLength() {
            return this.filteredData.length;
          },
          filteredData() {
            let result = this.tableData;
            const textFilterColumns = this.columns.filter(e => Object.prototype.hasOwnProperty.call(e, 'filterValue'));
            for(const column of textFilterColumns) {
              result = result.filter(e =>
                  e[column['key']].toLowerCase().includes(column['filterValue'].toLowerCase()));
            }
            const minFilterColumns = this.columns.filter(e => Object.prototype.hasOwnProperty.call(e, 'filterRange'));
            for(const column of minFilterColumns) {
              result = result.filter(e => e[column['key']] >= column['filterRange']['0']);
            }
            const maxFilterColumns = this.columns.filter(e => Object.prototype.hasOwnProperty.call(e, 'filterRange'));
            for(const column of maxFilterColumns) {
              result = result.filter(e => e[column['key']] <= column['filterRange']['1']);
            }
            return result;
          },
          paginationRequired() {
            return this.dataLength > this.perPage;
          },
        },
    };
</script>