import Vue from 'vue'
import App from './App.vue'
import BootstrapVue from 'bootstrap-vue';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';
import { LayoutPlugin } from 'bootstrap-vue';
import { BFormSelect } from 'bootstrap-vue'
import { BContainer } from 'bootstrap-vue'
import { BRow } from 'bootstrap-vue'
import { BCol } from 'bootstrap-vue'
import { BForm } from 'bootstrap-vue'
import { BFormRow } from 'bootstrap-vue'
import { BFormGroup } from 'bootstrap-vue'
import { BTable } from 'bootstrap-vue'
import { BPagination } from 'bootstrap-vue'

Vue.use(BootstrapVue);
Vue.use(LayoutPlugin);

Vue.component('b-container', BContainer);
Vue.component('b-form-select', BFormSelect);
Vue.component('b-row', BRow);
Vue.component('b-form-row', BFormRow);
Vue.component('b-col', BCol);
Vue.component('b-form', BForm);
Vue.component('b-form-group', BFormGroup);
Vue.component('b-table', BTable);
Vue.component('b-pagination', BPagination);

Vue.config.productionTip = false

new Vue({
  render: h => h(App),
}).$mount('#app')