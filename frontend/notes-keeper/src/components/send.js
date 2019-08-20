import React from 'react'
import Button from '@material-ui/core/Button';

export default class Send extends React.Component {
  render() {
    return (
      <Button variant="contained" color="primary">
        {/* {this.props.value} */}
        Send
      </Button>
    );
  }
}
