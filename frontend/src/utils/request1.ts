import axios from "axios";
import { CONFIG } from "@/config"

const instance = axios.create ({
  timeout: 10000,
  baseURL: `${CONFIG.API_HOST}/api`,
  headers: {
    "Content-Type": "application/json"
  }
})

instance.interceptors.request.use()

