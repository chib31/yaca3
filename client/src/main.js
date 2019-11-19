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
import { BDropdown } from 'bootstrap-vue';
import { BDropdownItem } from 'bootstrap-vue';
import { BDropdownDivider } from 'bootstrap-vue';
import { BNavbar } from 'bootstrap-vue';
import { BNavbarBrand } from 'bootstrap-vue';
import { BNavbarNav } from 'bootstrap-vue';
import { BNavItem } from 'bootstrap-vue';
import { BNavItemDropdown } from 'bootstrap-vue';
import { BDropdownForm } from 'bootstrap-vue';
import { BFormCheckboxGroup } from 'bootstrap-vue';
import { BFormCheckbox } from 'bootstrap-vue';
import Vue from 'vue';
import App from './App.vue';
import VueNouislider from 'vue-nouislider';
import { library } from '@fortawesome/fontawesome-svg-core';

import { faCalendarCheck } from '@fortawesome/free-regular-svg-icons';
import { faUsers } from '@fortawesome/free-solid-svg-icons';
import { faFilter } from '@fortawesome/free-solid-svg-icons';
import { faArrowUp } from '@fortawesome/free-solid-svg-icons';
import { faArrowDown } from '@fortawesome/free-solid-svg-icons';
import { faPlus } from '@fortawesome/free-solid-svg-icons';
import { faMinus } from '@fortawesome/free-solid-svg-icons';
import { faTimes } from '@fortawesome/free-solid-svg-icons';
import { faCaretUp } from '@fortawesome/free-solid-svg-icons';
import { faCaretDown } from '@fortawesome/free-solid-svg-icons';
import { faChartBar } from '@fortawesome/free-regular-svg-icons';
import { faTimesCircle } from '@fortawesome/free-regular-svg-icons';

import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

library.add(faCalendarCheck);
library.add(faUsers);
library.add(faChartBar);
library.add(faTimesCircle);
library.add(faTimes);
library.add(faFilter);
library.add(faArrowUp);
library.add(faArrowDown);
library.add(faPlus);
library.add(faMinus);
library.add(faCaretUp);
library.add(faCaretDown);

Vue.component('font-awesome-icon', FontAwesomeIcon);

Vue.use(BootstrapVue);
Vue.use(LayoutPlugin);
Vue.use(VueNouislider);

// Regex to speed up imports
// import \{ (.+?) \} from 'bootstrap-vue';
// Vue.component('b-', $1);
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
Vue.component('b-dropdown', BDropdown);
Vue.component('b-dropdown-item', BDropdownItem);
Vue.component('b-dropdown-divider', BDropdownDivider);
Vue.component('b-navbar', BNavbar);
Vue.component('b-navbar-brand', BNavbarBrand);
Vue.component('b-navbar-nav', BNavbarNav);
Vue.component('b-nav-item', BNavItem);
Vue.component('b-nav-item-dropdown', BNavItemDropdown);
Vue.component('b-dropdown-form', BDropdownForm);
Vue.component('b-form-checkbox-group', BFormCheckboxGroup);
Vue.component('b-form-checkbox', BFormCheckbox);

new Vue({
  render: h => h(App),
}).$mount('#app');