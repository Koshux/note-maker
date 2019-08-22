import React from 'react'
import Note from '../components/note'
import '@testing-library/jest-dom/extend-expect'
import {
  render,
  cleanup,
  fireEvent,
} from '@testing-library/react'

describe('Note component tests:', () => {
  // afterEach(cleanup)

  describe('Scenario: Update the note using useState hook `onKeyUp`:', () => {
    it('should update the note state as the user presses a key', () => {
      const { getByTestId } = render(<Note />)

      // Populate the input field.
      fireEvent.keyUp(getByTestId("note"), { key: 'T', code: 84 })
      fireEvent.keyUp(getByTestId("note"), { key: 'e', code: 84 })
      fireEvent.keyUp(getByTestId("note"), { key: 's', code: 84 })
      fireEvent.keyUp(getByTestId("note"), { key: 't', code: 84 })

      // TODO: Expect the note state to match the input field value.
      // 1. Ensure the props.note = 'test'
    })
  })
})
