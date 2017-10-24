import {
  AppFrame,
  Control,
  ModelEditor,
  WrapCard,
  CodeEditor,
  RenderDev
} from '../component'

const soul = {
  AppFrame,
  Control,
  ModelEditor,
  WrapCard,
  CodeEditor,
  RenderDev
};


const install = function (Vue, opts = {}) {
  Object.keys(soul).forEach((key) => {
    Vue.component(key, soul[key]);
  });
};

export default Object.assign(soul, {install});
