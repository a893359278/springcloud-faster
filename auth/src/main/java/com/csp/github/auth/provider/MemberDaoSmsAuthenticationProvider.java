//package com.csp.github.auth.provider;
//
//import cn.hutool.core.util.StrUtil;
//import com.csp.github.base.common.entity.DefaultResultType;
//import com.csp.github.base.common.exception.ServiceException;
//import java.util.Collections;
//import java.util.Objects;
//import javax.annotation.Resource;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//
///**
// * @author 陈少平
// * @date 2019-12-30 19:42
// */
//public class MemberDaoSmsAuthenticationProvider implements AuthenticationProvider {
//
//
//    @Resource
//    SmsService smsService;
//    @Resource
//    MemberServiceCommon memberServiceCommon;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        MemberSmsAuthenticationToken token = (MemberSmsAuthenticationToken) authentication;
//        String code = smsService.getAuthCode(token.getPrincipal(), token.getUnitId());
//        if (StrUtil.isEmpty(code)) {
//            throw new ServiceException("验证码已过期");
//        }
//        if (!code.equals(authentication.getCredentials())) {
//            throw new ServiceException("验证码不正确");
//        }
//        // 判断该单元下，是否有该用户
//        UmsMember member = memberServiceCommon.getByPhone(token.getPrincipal(), token.getUnitId());
//        if (Objects.isNull(member)) {
//            throw new ServiceException(DefaultResultType.NOT_EXIST);
//        }
//
//        return new MemberAuthenticationToken(Collections.EMPTY_LIST, member.getUsername(),
//                member.getPassword(), member.getUnitId(), member.getId());
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return MemberSmsAuthenticationToken.class.isAssignableFrom(authentication);
//    }
//}
