import BootstrapVue from 'bootstrap-vue';
import { LayoutPlugin } from 'bootstrap-vue';
import { BFormSelect } from 'bootstrap-vue';
import { BContainer } from 'bootstrap-vue';
import { BRow } from 'bootstrap-vue';
import { BCol } from 'bootstrap-vue';
import { BForm } from 'bootstrap-vue';
import { BFormRow } from 'bootstrap-vue';
import { BFormGroup } from 'bootstrap-vue';
import { BTable } from 'bootstrap-vue';
import { BPagination } from 'bootstrap-vue';
import { BInputGroup } from 'bootstrap-vue';
import { BButton } from 'bootstrap-vue';
import { BInputGroupAppend } from 'bootstrap-vue';
import { BFormInput } from 'bootstrap-vue';
import Vue from 'vue';
import App from './App.vue';
import VueNouislider from 'vue-nouislider';
import { library } from '@fortawesome/fontawesome-svg-core';

import { faCalendarCheck } from '@fortawesome/free-regular-svg-icons';
import { faUsers } from '@fortawesome/free-solid-svg-icons';
import { faFilter } from '@fortawesome/free-solid-svg-icons';
import { faArrowUp } from '@fortawesome/free-solid-svg-icons';
import { faArrowDown } from '@fortawesome/free-solid-svg-icons';
import { faChartBar } from '@fortawesome/free-regular-svg-icons';
import { faTimesCircle } from '@fortawesome/free-regular-svg-icons';

import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

library.add(faCalendarCheck);
library.add(faUsers);
library.add(faChartBar);
library.add(faTimesCircle);
library.add(faFilter);
library.add(faArrowUp);
library.add(faArrowDown);

Vue.component('font-awesome-icon', FontAwesomeIcon);

Vue.use(BootstrapVue);
Vue.use(LayoutPlugin);
Vue.use(VueNouislider);

Vue.component('b-container', BContainer);
Vue.component('b-form-select', BFormSelect);
Vue.component('b-row', BRow);
Vue.component('b-form-row', BFormRow);
Vue.component('b-col', BCol);
Vue.component('b-form', BForm);
Vue.component('b-form-group', BFormGroup);
Vue.component('b-table', BTable);
Vue.component('b-pagination', BPagination);
Vue.component('b-input-group', BInputGroup);
Vue.component('b-button', BButton);
Vue.component('b-input-group-append', BInputGroupAppend);
Vue.component('b-form-input', BFormInput);

new Vue({
  render: h => h(App),
}).$mount('#app');