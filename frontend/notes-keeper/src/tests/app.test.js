import App from '../app'
import React from 'react'
import ReactDOM from 'react-dom'

describe('App entry point', () => {
  describe('Scenario: Rendering the App should', () => {
    it('render without crashing', () => {
      const div = document.createElement('div')
      ReactDOM.render(<App />, div)
      ReactDOM.unmountComponentAtNode(div)
    })
  })
})
