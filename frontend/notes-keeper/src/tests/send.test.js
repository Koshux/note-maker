import React from 'react'
import Send from '../components/send'
import '@testing-library/jest-dom/extend-expect'
import {
  render,
  cleanup,
  fireEvent
} from '@testing-library/react'

describe('Send component tests:', () => {
  // afterEach(cleanup)

  describe('Scenario: User creates a note:', () => {
    it('should send the note to the database', () => {
      // const { getByTestId } = render(<Send />)
      // fireEvent.click(getByTestId("send"))
      // const send = await waitForElement(() => getByTestId())

      // TODO: Tests for note persistance
      // 1. Ensure correct URL is called.
      // 2. Ensure requestOpts match JSON:API standard.
      // 3. Check the query params of the fetch(...) POST request.
    })
  })

  describe('Scenario: Updating the datatable once the note is sent:', () => {
    describe('Scenario: Once the user clicks to send the note:', () => {
      it('should refresh the datatable', () => {
        // const { getByTestId } = render(<Send />)
        // fireEvent.click(getByTestId("send"))

        // TODO: Ensure that datatable refresh occurred on note persistance.
        // 1. mock `materialTableRef.current.onQueryChange` to check call count.
      })
    })
  })
})
