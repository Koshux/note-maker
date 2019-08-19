import React from 'react'

export default class NoteList extends React.Component {
  renderNotes () {
    const notes = Object.values(this.props.notes)
    return notes.map(note => <div><h2>{note.title}</h2></div>)
  }

  render () {
    return (
      <div>
        { this.renderNotes() }
      </div>
    )
  }
}
