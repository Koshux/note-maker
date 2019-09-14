import React from 'react'
import '@testing-library/jest-dom/extend-expect'
import NotesDataTable from '../components/notes-datatable'
import { render, cleanup, fireEvent } from '@testing-library/react'

describe('NotesDataTable component tests:', () => {
  // afterEach(cleanup)

  describe('Scenario: Loading the first page of notes:', () => {
    beforeEach(() => {
      // TODO: Mock fetch request payload
      // Mock request with payload of notes:
      // mockData: [
      //   {
      //     id: '1',
      //     type: 'notes',
      //     attributes: {
      //       title: 'default',
      //       desciption: 'test-description',
      //       timestamp: 1566161999000
      //     }
      //   },
      //   {
      //     id: '2',
      //     type: 'notes',
      //     attributes: {
      //       title: 'default',
      //       desciption: 'test-description',
      //       timestamp: 1566428254000
      //     }
      //   } ... { 8 more }
      // ]
    })

    it('should request the first 10 records', () => {
      // TODO: Tests for first page of notes loaded and sorted in `desc`.
      // 1. Ensure correct URL is called.
      // 2. Ensure requestOpts match JSON:API standard.
      // 3. Check the params:
      //   3.1. sort=-creationDate of the fetch(...) GET request
      //   3.2. offset=00&limit=10 of the fetch(...) GET request
    })

    it('should have returned a payload for `material-table` to process', () => {
      // 4. Check data matches:
      //   4.1. resolved promise: {
      //     data: mockData,
      //     page: 0,
      //     totalCount: 10
      //   }
    })
  })

  describe('Scenario: Loading the next page of notes:', () => {
    beforeEach(() => {
      // TODO: Mock fetch request payload
      // Mock request with payload of notes:
      // mockData: [
      //   {
      //     id: '11',
      //     type: 'notes',
      //     attributes: {
      //       title: 'default',
      //       desciption: 'test-description',
      //       timestamp: 1566161999000
      //     }
      //   },
      //   {
      //     id: '12',
      //     type: 'notes',
      //     attributes: {
      //       title: 'default',
      //       desciption: 'test-description',
      //       timestamp: 1566428254000
      //     }
      //   } ... { 8 more }
      // ]
    })

    it('should request the next page (next 10 records)', () => {
      // TODO: Tests for next page of notes loaded and sorted in `desc`.
      // 1. Ensure correct URL is called.
      // 2. Ensure requestOpts match JSON:API standard.
      // 3. Check the params:
      //   3.1. sort=-creationDate of the fetch(...) GET request
      //   3.1. offset=10&limit=10 of the fetch(...) GET request
    })

    it('should have returned a payload for `material-table` to process', () => {
      // 4. Check data matches:
      //   4.1. resolved promise: {
      //     data: mockData,
      //     page: 1,
      //     totalCount: 10
      //   }
    })
  })

  describe('Scenario: Sorting the notes:', () => {
    beforeEach(() => {
      // TODO: Mock fetch request payload
      // Mock request with payload of notes:
      // mockData: [
      //   {
      //     id: '12',
      //     type: 'notes',
      //     attributes: {
      //       title: 'default',
      //       desciption: 'test-description',
      //       timestamp: 1566428254000
      //     }
      //   },
      //   {
      //     id: '11',
      //     type: 'notes',
      //     attributes: {
      //       title: 'default',
      //       desciption: 'test-description',
      //       timestamp: 1566161999000
      //     }
      //   } ... { 8 more }
      // ]
    })

    it('request the notes endpoint in the API', () => {
      // TODO: Tests for notes sorted creation date in `asc` are loaded
      // 1. Ensure correct URL is called.
      // 2. Ensure requestOpts match JSON:API standard.
      // 3. Check the params:
      //   3.1. sort=creationDate of the fetch(...) GET request
      //   3.1. offset=10&limit=10 of the fetch(...) GET request
    })
  })

  describe('Scenario: No response is received:', () => {
    beforeEach(() => {
      // TODO: Tests for next page of notes loaded
      // Mock request with payload of notes:
      // mockData: null
    })

    it('request the notes endpoint in the API', () => {
      // 1. Ensure correct URL is called.
      // 2. Ensure requestOpts match JSON:API standard.
      // 3. Check the params:
      //   3.1. sort=creationDate of the fetch(...) GET request
      //   3.1. offset=0&limit=10 of the fetch(...) GET request
    })

    it('should have returned a payload for `material-table` to process', () => {
      // 4. Check data matches:
      //   4.1. resolved promise: {
      //     data: [],
      //     page: 0,
      //     totalCount: 0
      //   }
    })
  })

  describe('Scenario: An error response is received while loading the notes:', () => {
    beforeEach(() => {
      // TODO: Tests for next page of notes loaded
      // Mock request with payload of notes:
      // mockData: {
      //   errors: ..., ...
      // }
    })

    it('request the notes endpoint in the API', () => {
      // 1. Ensure correct URL is called.
      // 2. Ensure requestOpts match JSON:API standard.
      // 3. Check the params:
      //   3.1. sort=creationDate of the fetch(...) GET request
      //   3.1. offset=0&limit=10 of the fetch(...) GET request
    })

    it('should have returned a payload for `material-table` to process', () => {
      // 4. Check data matches:
      //   4.1. resolved promise: {
      //     data: [],
      //     page: 0,
      //     totalCount: 0
      //   }
    })
  })

  describe('Scenario: Something went wrong and an error was caught:', () => {
    // TODO: Tests for failure in request led to caught exception.
    it('should have rejected the promise', () => {
      // Check that the promise was rejected with the error instead of resolved.
    })
  })
})
