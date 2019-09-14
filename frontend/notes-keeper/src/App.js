import React from 'react'
import IndexPage from './pages/index'

class App extends React.PureComponent {
  ref = React.createRef()

  render () {
    return (
      <div className="app">
        <IndexPage materialTableRef={this.ref} />
      </div>
    )
  }
}

export default App
