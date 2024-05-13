import {
  Body,
  Controller,
  FileTypeValidator,
  HttpCode,
  HttpStatus,
  MaxFileSizeValidator,
  Param,
  ParseFilePipe,
  Patch,
  Post,
  Query,
  Req,
  UploadedFile,
  UseGuards,
  UseInterceptors,
} from '@nestjs/common';
import { FileInterceptor } from '@nestjs/platform-express';
import { AuthenticationGuard } from 'src/auth/authUtils/authentication.guard';
import { RequestModel } from 'src/helpers/requestmodel';
import { UserService } from 'src/services/user.service';

@Controller('/api/v1/user')
export class UserController {
  constructor(private userService: UserService) {}

  @Patch('profile')
  @HttpCode(200)
  @UseGuards(AuthenticationGuard)
  @UseInterceptors(FileInterceptor('avatar'))
  async updateUserInfo(
    @Req() req: RequestModel,
    @Body()
    body: {
      name: string;
      gender: string;
      dateOfBirth: Date;
      address: string;
      mobile: string;
    },
    @UploadedFile(
      new ParseFilePipe({
        validators: [
          new MaxFileSizeValidator({ maxSize: 20000 }),
          new FileTypeValidator({ fileType: '.(png|jpeg|jpg)' }),
        ],
      }),
    )
    file: Express.Multer.File,
  ) {
    return await this.userService.updateUserInfo({
      userId: req.user.userId,
      bodyUpdate: body,
      file,
    });
  }

  @Patch('password')
  @HttpCode(200)
  @UseGuards(AuthenticationGuard)
  async updatePassword(
    @Body()
    body: {
      currentPassword: string;
      newPassword: string;
      newPasswordConfirm: string;
    },
    @Req() req: RequestModel,
  ) {
    return await this.userService.updatePassword({
      userId: req.user.userId,
      ...body,
    });
  }

  @Patch('tokenFirebase')
  @HttpCode(200)
  async updateTokenFirebase(
    @Body()
    body: {
      tokenFirebase: string;
      userId: string;
    },
  ) {
    return await this.userService.updateTokenFirebase(body);
  }
}
