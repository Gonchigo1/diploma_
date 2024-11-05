FROM node:18-alpine

ENV NODE_ENV production

WORKDIR /app
RUN addgroup -g 1001 -S nodejs
RUN adduser -S nextjs -u 1001
USER nextjs

COPY package.json package-lock.json ./
COPY .env.production .env.production
COPY public ./public
COPY --chown=nextjs:nodejs .next ./.next
COPY node_modules ./node_modules

EXPOSE 3001
CMD ["npm", "start"]