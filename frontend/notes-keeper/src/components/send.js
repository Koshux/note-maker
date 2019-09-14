import React from 'react'
import Utils from './utils'
import Button from '@material-ui/core/Button'

export default function Send (props) {
  // Build JSON:API compliant POST request body.
  const setupRequestData = () => {
    return {
      data: {
        type: 'notes',
        attributes: {
          description: props.note.trim()
        }
      }
    }
  }

  // Save the note to the DB.
  const handleClick = () => {
    return Utils.createNote(JSON.stringify(setupRequestData())).then(() => {
      // Refresh the notes datatable.
      props.materialTableRef.current &&
        props.materialTableRef.current.onQueryChange()
    })
  }

  // No notes found.
  const disabled = props.note == null || props.note.length === 0

  return (
    <Button
      color='primary'
      variant='contained'
      fullWidth={true}
      onClick={()=> handleClick}
      disabled={disabled}
      data-testid="send"
    >
      Send
    </Button>
  )
}
