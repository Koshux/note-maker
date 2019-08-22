import React from 'react'
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
    let url = 'lanzonprojects/api/notes'
    requestOpts.body = JSON.stringify(setupRequestData())
    return fetch(url, requestOpts).then(response => response.json())
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
