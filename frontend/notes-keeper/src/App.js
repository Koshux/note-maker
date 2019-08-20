// import logo from './logo.svg';
import IndexPage from './pages/index'
import React from 'react';

class App extends React.PureComponent {
  state = {
    notes: {
      1: {
        _id: 1,
        title: 'Hello World!',
        description: 'First note.',
        creationDate: new Date()
      },
      2: {
        _id: 1,
        title: 'Hello World!',
        description: 'First note.',
        creationDate: new Date()
      }
    }
  }

  render () {
    return (
      <div className="app">
        <IndexPage notes={this.state.notes} />
      </div>
    );
  }
}

export default App;
