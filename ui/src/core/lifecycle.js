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

function fire(stage,soul){
  if(soul[stage]) soul[stage](soul)
}

export {
  fire
}
