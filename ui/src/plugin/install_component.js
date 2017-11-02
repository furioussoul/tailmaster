import {
  Control,
  ModelEditor,
  WrapCard,
  CodeEditor,
  RenderDev,
  WrapModal,
  WrapSelect,
  WrapUpload
} from '../component'

const soul = {
  Control,
  ModelEditor,
  WrapCard,
  CodeEditor,
  RenderDev,
  WrapModal,
  WrapSelect,
  WrapUpload
};


const install = function (Vue, opts = {}) {
  Object.keys(soul).forEach((key) => {
    Vue.component(key, soul[key]);
  });
};

export default Object.assign(soul, {install});
