package com.aromano.bragiassignment.domain.core

interface UseCase<Req, Res> {

    suspend fun execute(req: Req): Outcome<Res>

}