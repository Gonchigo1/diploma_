FROM node:16-alpine

USER node:node
WORKDIR /home/node/app
COPY package.json yarn.lock ./
COPY node_modules node_modules
COPY src src

EXPOSE 3000
CMD ["node", "src/server.js"]
