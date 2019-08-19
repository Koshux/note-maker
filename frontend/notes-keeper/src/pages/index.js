import React from 'react'
import NoteList from '../components/notelist'

export default class IndexPage extends React.Component {
  render () {
    return (
      <div>
        <h1>Notes</h1>
        <NoteList notes={this.state.notes} />
      </div>
    )
  }
}
