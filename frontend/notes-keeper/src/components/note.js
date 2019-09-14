import React from 'react'
import Input from '@material-ui/core/Input'
import { makeStyles } from '@material-ui/core/styles'

const useStyles = makeStyles(theme => ({
  container: {
    display: 'flex',
    flexWrap: 'wrap',
  },
  input: {
    margin: theme.spacing(1),
  },
}))

export default function Note (props) {
  const classes = useStyles()

  function handleKeyUp (event) {
    props.setNote(event.target.value)
  }

  return (
    <div className={classes.container}>
      <Input
        fullWidth={true}
        onKeyUp={handleKeyUp}
        className={classes.input}
        defaultValue={props.note}
        placeholder='Enter note description'
        inputProps={{maxLength: 100,'aria-label': 'description'}}
        data-testid="note"
      />
    </div>
  )
}
