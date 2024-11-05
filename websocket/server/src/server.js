const express = require('express')
const {createServer} = require('http')
const {Server} = require('socket.io')

// import {createAdapter} from '@socket.io/redis-adapter'
// import {createClient} from 'redis'

const {getNowDate} = require('./util.js')

const app = express()

const httpServer = createServer(app)
const io = new Server(httpServer, {
  cors: {
    origin: [
      'http://localhost:3000',
      'http://localhost:3001',
      'http://localhost:3002',
    ],
    methods: ['HEAD', 'GET', 'POST', 'OPTIONS'],
    // credentials: true
  }
})

const authKey = 'Jc1ZvJZp@qsJ9l0r@ve9LG59CO^s7*e$'

const paymentNamespace = io.of('payment')
paymentNamespace.on('connection', (socket) => {
  const clientIp = socket.handshake.headers['x-forwarded-for'] || socket.request.socket.remoteAddress.split(':')[3]
  console.log(getNowDate() + 'connected to payment: ' + clientIp)

  socket.on('join-room', (payload) => {
    if (payload.room) {
      console.log(getNowDate() + clientIp + ' payment joining room: ' + payload.room)
      socket.join(payload.room)
    }
  })

  socket.on('payment-result', (payload) => {
    console.log(getNowDate() + 'payment result: ' + payload.authKey)
    if (payload.authKey === authKey) {
      console.log(getNowDate() + 'payment result: ' + payload.message + ' in room: ' + payload.room)
      paymentNamespace.to(payload.room).emit('payment-result', payload.message)
    }
  })
})
paymentNamespace.use((socket, next) => {
  next()
})

const ssoNamespace = io.of('sso')
ssoNamespace.on('connection', (socket) => {
  const clientIp = socket.handshake.headers['x-forwarded-for'] || socket.request.socket.remoteAddress.split(':')[3]
  console.log(getNowDate() + 'connected to sso: ' + clientIp)

  socket.on('join-room', (payload) => {
    if (payload.room) {
      console.log(getNowDate() + clientIp + ' sso joining room: ' + payload.room)
      socket.join(payload.room)
    }
  })

  socket.on('data-result', (payload) => {
    // console.log(getNowDate() + 'data-result: ' + payload.authKey)
    const payloadObj = JSON.parse(payload)
    if (payloadObj.authKey === authKey) {
      console.log(getNowDate() + 'data-result: ' + payloadObj.message + ' in room: ' + payloadObj.room)
      ssoNamespace.to(payloadObj.room).emit('data-result', payloadObj.message)
    }
  })

  socket.on('login-result', (payload) => {
    // console.log(getNowDate() + 'login-result: ' + payload.authKey)
    const payloadObj = JSON.parse(payload)
    if (payloadObj.authKey === authKey) { // only call from backend service
      console.log(getNowDate() + 'login result: ' + payloadObj.message + ' in room: ' + payloadObj.room)
      ssoNamespace.to(payloadObj.room).emit('login-result', payloadObj.message)
    }
  })
})
ssoNamespace.use((socket, next) => {
  next()
})

// io.on('disconnect', (socket) => {
//   console.log('disconnected: ' + socket.request.connection.remoteAddress)
// })

io.serveClient(false)
// io.path('/sock')

httpServer.listen(3000)
console.log(getNowDate() + 'started socket on port: 3000')

// adapter
// const pubClient = createClient({host: 'localhost', port: 6379}) // redis
// const subClient = pubClient.duplicate()
// io.adapter(createAdapter(pubClient, subClient))

// redis
// Promise.all([pubClient.connect(), subClient.connect()]).then(() => {
//   io.listen(3000)
// })
