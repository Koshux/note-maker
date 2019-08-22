import React from 'react'
import Utils from './utils'
import Button from '@material-ui/core/Button'

// JSON:API headers for POST request.
const requestOpts = {
  method: 'POST',
  cache: 'no-cache',
  headers: { 'Content-Type': 'application/vnd.api+json' }
}

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
    requestOpts.body = JSON.stringify(setupRequestData())
    return fetch(Utils.ENDPOINT, requestOpts)
      .then(response => response.json())
      .then(() => {
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
      onClick={()=> handleClick()}
      disabled={disabled}>
      Send
    </Button>
  )
}
