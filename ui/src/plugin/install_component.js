import {
  Control,
  ModelEditor,
  WrapCard,
  CodeEditor,
  RenderDev,
  WrapModal,
  WrapSelect,
  WrapUpload,
  FormInput,
  FormSelect
} from '../component'

const soul = {
  Control,
  ModelEditor,
  WrapCard,
  CodeEditor,
  RenderDev,
  WrapModal,
  WrapSelect,
  WrapUpload,
  FormInput,
  FormSelect
};

const install = function (Vue, opts = {}) {
  Object.keys(soul).forEach((key) => {
    Vue.component(key, soul[key]);
  });
};

export default Object.assign(soul, {install});
