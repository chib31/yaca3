<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <b-container fluid>
    <hr/>
    <b-row class="justify-content-md-center mb-1">
      <b-col md="auto">
        <b-form inline>
          <label> Showing </label>
          <b-form-select v-model="perPage" :options="perPageOptions" size="sm" class="mx-1"/>
          <label> of {{ dataLength }} results </label>
        </b-form>
      </b-col>
    </b-row>
    <b-row class="justify-content-md-center">
      <b-col md="auto">
        <b-pagination v-model="currentPage"
                      :total-rows="dataLength"
                      :per-page="perPage"
                      aria-controls="stats-table"
                      class="small"/>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <b-table id="stats-table"
                 :items="tableData"
                 :fields="columns"
                 :perPage="perPage"
                 :currentPage="currentPage"
                 :busy="tableLoading"
                 thead-class="text-nowrap"
                 tbody-class="text-nowrap"
                 style="text-align: left;"
                 sticky-header="1000px"
                 striped hover small sort-icon-left>
          <template v-slot:table-busy>
            <div class="text-center text-danger my-2">
              <b-spinner class="align-middle"></b-spinner>
              <strong>Loading...</strong>
            </div>
          </template>
          <template v-slot:cell(index)="tableData">
            {{ (tableData.index + 1) + (perPage * (currentPage - 1)) }}
          </template>
          <template v-slot:cell(strikeRate)="tableData">
            {{ tableData.value === '' ? 'n/a' : tableData.value.toFixed(1) }}
          </template>
          <template v-slot:cell(average)="tableData">
            {{ tableData.value === '' ? 'n/a' : tableData.value.toFixed(1) }}
          </template>
          <template v-slot:cell(percentOfTotal)="tableData">
            {{ tableData.value === '' ? 'n/a' : tableData.value.toFixed(1) + '%' }}
          </template>
          <template v-slot:cell(economy)="tableData">
            {{ tableData.value === '' ? 'n/a' : tableData.value.toFixed(2) }}
          </template>
          <template v-slot:cell(playerName)="tableData">
            {{ tableData.value }}
          </template>
          <template v-slot:cell(fixture)="tableData">
            {{ tableData.value }}
          </template>
        </b-table>
      </b-col>
    </b-row>
    <b-row class="justify-content-md-center">
      <b-col md="auto">
        <b-pagination v-model="currentPage"
                      :total-rows="dataLength"
                      :per-page="perPage"
                      aria-controls="stats-table"
                      class="small"/>
      </b-col>
    </b-row>
  </b-container>
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
            tableLoading: Boolean
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
                    {value: 'all', text: 'All'}
                ],
            };
        },
        computed: {
            dataLength() {
                return this.tableData.length;
            }
        },
    };
</script>