FROM node:12-slim
WORKDIR /app/front
COPY package.json package-lock.json ./
RUN npm install
COPY . ./
EXPOSE 3000
CMD ["npm", "start"]
