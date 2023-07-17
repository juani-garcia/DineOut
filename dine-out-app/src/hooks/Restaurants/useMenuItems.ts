import { useMethod } from "../auth/useMethod";
import { paths } from "@/common/const";
import { type AxiosResponse } from "axios";
import { HttpMethod } from "@/types/enums/HTTPMethod";

// eslint-disable-next-line @typescript-eslint/explicit-function-return-type
export function useMenuItems() {
  const { isLoading, requestMethod } = useMethod();

  async function menuItems(uri: string): Promise<AxiosResponse> {
    return await requestMethod({
      method: HttpMethod.GET,
      // eslint-disable-next-line @typescript-eslint/restrict-plus-operands
      url: uri,
    });
  }

  return { isLoading, menuItems };
}
