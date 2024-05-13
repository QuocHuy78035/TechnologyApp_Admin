import { Controller, Get, HttpCode, HttpStatus, Param } from '@nestjs/common';
import { CategoryService } from 'src/services/category.service';

@Controller('/api/v1/category')
export class CategoryController {
  constructor(private categoryService: CategoryService) {}

  @Get('/')
  @HttpCode(200)
  async getAllCategories() {
    const categories = await this.categoryService.getAllCategories();
    return categories;
  }

  @Get('/:id')
  @HttpCode(200)
  async getCategory(@Param('id') id: string) {
    const category = await this.categoryService.getCategory(id);
    return category;
  }

  async addCategory() {}

  async updateCategory() {}
  async deleteCategory() {}
}
