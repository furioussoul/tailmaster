import {
  AppFrame,
  Control,
  Drop,
  DropPanel,
  Editor,
  WrapCard
} from '../component'

const soul = {
  AppFrame,
  Control,
  Drop,
  DropPanel,
  Editor,
  WrapCard
};


const install = function (Vue, opts = {}) {
  Object.keys(soul).forEach((key) => {
    Vue.component(key, soul[key]);
  });
};

export default Object.assign(soul, {install});
