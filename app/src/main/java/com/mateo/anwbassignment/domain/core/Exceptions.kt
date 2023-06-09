package com.mateo.anwbassignment.domain.core

// could add further meta data to these for analytics for example

open class AssignmentExceptions : Exception()

class NetworkException : AssignmentExceptions()

class ServerException : AssignmentExceptions()

class EmptyResponse : AssignmentExceptions()

class EventNotFound : AssignmentExceptions()