import {
  AppFrame,
  Control,
  Drop,
  DropPanel,
  Editor
} from '../component'

const soul = {
  AppFrame,
  Control,
  Drop,
  DropPanel,
  Editor
};


const install = function (Vue, opts = {}) {
  Object.keys(soul).forEach((key) => {
    Vue.component(key, soul[key]);
  });
};

export default Object.assign(soul, {install});
