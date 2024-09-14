import { AppUser } from "src/app/common/types/user"

export interface AuthenticationResponse{
    user: AppUser
    accessToken: string
    refreshToken: string
}