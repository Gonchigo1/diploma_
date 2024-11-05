import NextAuth from 'next-auth'
import CredentialsProvider from 'next-auth/providers/credentials'

import {logIn} from '../../../common/services/auth/auth'

export default NextAuth({
  providers: [
    CredentialsProvider({
      name: 'Credentials',

      async authorize (credentials, _req) {
        // console.log(credentials, 'credentials')
        return logIn(credentials?.username, credentials?.password)
            .then(response => {
              if (response.result === true && response.data) {
                return Promise.resolve(response.data)
              } else {
                // console.log(response.message, 'response message')
                return Promise.reject(new Error(response.message))
              }
            })
      }
    })
  ],
  secret: process.env.NEXTAUTH_SECRET,

  session: {
    strategy: 'jwt',
    name: 'portal',
  },

  jwt: {
    secret: process.env.NEXTAUTH_SECRET
  },

  pages: {
    signIn: '/auth/signin',
    signOut: '/auth/signout',
    error: '/auth/signin', // Error code passed in query string as ?error=
    verifyRequest: '/auth/verify', // Used for check email page
    newUser: undefined // If set, new users will be directed here on first sign in
  },

  callbacks: {
    jwt: ({token, user}) => {
      if (user) {
        token.token = user.token
        token.user = user
      }

      return token
    },
    async session ({session, token, _user}) {
      if (token) {
        session.user = {
          name: token?.user?.name,
          email: token?.user?.email,
          image: token?.user?.image,
          id: token?.user?.id
        }
        session.token = token?.token
        session.expires = token?.user?.expires
        session.businessRole = token?.user?.businessRole?.role
        session.applicationRoles = token?.user?.businessRole?.applicationRoles
      }
      return session
    }
  },

  events: {},
  debug: true
})