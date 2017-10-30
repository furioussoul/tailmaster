import {
  findSoulByCTypeUp
} from '../helper/soul_helper'

function isFormItem(drag) {
  switch (drag.type) {
    case 'Input':
    case 'CheckboxGroup':
    case 'RadioGroup':
    case 'Select':
      return true;
    default:
      return false;
  }
}

export function registerFormItem(soul, ancestorSoul) {
  if(isFormItem(soul)) reset(soul,ancestorSoul)
}

function reset(soul,ancestorSoul) {
  let form = findSoulByCTypeUp('Form', soul, ancestorSoul);
  form.model.model.value[soul.model.formKey.value] = soul.model.value.value
  let copy = soul.model.formKey.value
  Object.defineProperty(soul.model.formKey, 'value', {
    set: (n) => {
      console.log(form.model.model.value)
      delete form.model.model.value[soul.model.formKey.value]
      form.model.model.value[n] = soul.model.value.value
      copy = n
    },
    get: () => {
      return copy
    }
  })

  let copyValue = soul.model.value.value
  Object.defineProperty(soul.model.value, 'value', {
    set: (n) => {
      console.log(n)
      form.model.model.value[soul.model.formKey.value] = n
      copyValue = n
    },
    get: () => {
      return copyValue
    }
  })
}

function fire(stage,soul){
  if(soul[stage]) soul[stage](soul)
}

export {
  fire
}
