/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/6
 */

package cn.rtast.rob.interceptor

import cn.rtast.rob.entity.IMessage

public abstract class FunctionalCommandInterceptor<M : IMessage> : IFunctionalLocalCommandInterceptor<M>
