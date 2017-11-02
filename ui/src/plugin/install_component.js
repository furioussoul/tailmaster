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
  FormSelect,
  FormNumber,
  FormDate,
  FormUpload
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
  FormSelect,
  FormNumber,
  FormDate,
  FormUpload
};

const install = function (Vue, opts = {}) {
  Object.keys(soul).forEach((key) => {
    Vue.component(key, soul[key]);
  });
};

export default Object.assign(soul, {install});
