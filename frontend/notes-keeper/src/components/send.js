import React from 'react'
import Button from '@material-ui/core/Button';

export default class Send extends React.Component {
  handleClick () {
    // 1. Validate note passed as props.note
    // - Ensure note <= 100
    //   - Done in note.js but requires double checks here.
    // - Input field data sanitzation
    // 2. POST JSON-API request
    // 3. Update DataTable
  }

  render() {
    // const disabled = this.props.note == null
    const disabled = this.props.note == null || this.props.note.length === 0
    console.log('Send props:', this.props)
    return (
      <Button
        color="primary"
        variant="contained"
        fullWidth={true}
        disabled={disabled}
        onClick={this.handleClick}>
        Send
      </Button>
    );
  }
}
